import React, { useEffect, useState } from 'react';
import { Input, Button } from 'antd';
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import mila from 'markdown-it-link-attributes';
import mdKatex from '@traptitech/markdown-it-katex';
import { EventSourcePolyfill } from 'event-source-polyfill';
import 'katex/dist/katex.min.css'
// import 'github-markdown-css/github-markdown-light.css'
// import 'highlight.js/styles/base16'
import './hljs.css'
import styles from './index.less';

function uuid() {
  var s = [];
  var hexDigits = '0123456789abcdef';
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = '4'; // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = '-';

  var uuid = s.join('');
  return uuid;
}

const md = new MarkdownIt({
  linkify: true,
  breaks: true,
  highlight: (str: string, lang: string, attrs: string): string => {
    let content = str;
    console.log('lang==>', lang)
    if (lang && hljs.getLanguage(lang)) {
      try {
        content = hljs.highlight(str, {
          language: lang,
          ignoreIllegals: true,
        }).value;
      } catch (e) {
        console.log(e);
        return str;
      }
    } else {
      content = md.utils.escapeHtml(str);
    }
    return `<pre class="hljs" style="max-width: 50vw; overflow: auto"><code>${content}</code></pre>`;
  },
});
md.use(mdKatex, {
  blockClass: 'katexmath-block rounded-md p-[10px]',
  errorColor: ' #cc0000',
});
md.use(mila, { attrs: { target: '_blank', rel: 'noopener' } });

function ChatAI() {
  const [messages, setMessages] = useState<
    Array<{ key: string; question: string; answer: string }>
  >([]);
  const [inputValue, setInputValue] = useState<string>('');
  const [question, setQuestion] = useState('');

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleSendMessage = () => {
    if (inputValue.trim() !== '') {
      setInputValue('');
      setQuestion(inputValue);

      const key = uuid();
      setMessages([...messages, { key, question: inputValue, answer: '' }]);
    }
  };
  useEffect(() => {
    if (!question) return;

    let uid = window.localStorage.getItem('uid');
    if (uid == null || uid == '' || uid == 'null') {
      uid = uuid();
    }

    let sse;
    let text = '';
    const eventSource = new EventSourcePolyfill(
      'http://38.55.129.58/api/ai/chat?message=' + question,
      {
        headers: {
          uid,
        },
      },
    );

    eventSource.onopen = (event) => {
      sse = event.target;
    };

    eventSource.onmessage = (event) => {
      if (event.data == '[DONE]') {
        console.log('text', text);
        // text = '';
        if (sse) {
          sse.close();
        }
        return;
      }
      let json_data = JSON.parse(event.data);
      if (json_data.content == null || json_data.content == 'null') {
        text = '';
        return;
      }
      text = text + json_data.content;

      const message = messages.find((i) => i.question === question);
      message.answer = text;
      console.log('text==>', text);
      setMessages([...messages]);
    };
    eventSource.onerror = (event) => {
      console.log('onerror', event);
      alert('服务异常请重试并联系开发者！');
      if (event.readyState === EventSource.CLOSED) {
        console.log('connection is closed');
      } else {
        console.log('Error occured', event);
      }
      event.target.close();
    };
    eventSource.addEventListener('customEventName', (event) => {
      console.log('Message id is ' + event.lastEventId);
    });
    return () => {
      eventSource.removeEventListener('customEventName', (event) => {
        console.log('remove eventSource');
      });
    };
  }, [question]);
  return (
    <div className={styles.chatAI}>
      <div className={styles.chatFlow}>
        {(messages || []).map((item) => (
          <div className={styles.chatItem} key={item.key}>
            <div className={styles.title}>问题：{item.question}</div>
            <div
              className={styles.content}
              dangerouslySetInnerHTML={{ __html: md.render(item.answer) }}
            />
          </div>
        ))}
      </div>

      <Input
        className={styles.chatBtn}
        placeholder="请输入"
        value={inputValue}
        onChange={handleInputChange}
        // onPressEnter={handleSendMessage}
        suffix={
          <Button type="primary" onClick={handleSendMessage}>
            发送
          </Button>
        }
      />
    </div>
  );
}

export default ChatAI;
