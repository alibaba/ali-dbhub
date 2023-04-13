const loaderUtils = require('loader-utils');

const hashCache = { };

module.exports.default = function loader(source) {

  console.log('执行了我loader');
  const option = loaderUtils.getOptions(this) || {};
  const themes = option.themes || ['default'];
  const hashLength = option.hashLength || 5;
  const rootSelector = option.rootSelector || ':root';
  const useGlobal = option.useGlobal || false;
  const declarations = { };
  themes.forEach(t => declarations[t] = []);
  const positions = findExpPositions(source);
  let lastIndex = 0;
  let result = '';
  positions.forEach(([start, end]) => {
    const exp = source.substring(start, end);
    const key = exp.replace(/(\s|\n)+/g, '');
    let hash = hashCache[key];
    if (!hash) {
      hash = hashCache[key] = loaderUtils.getHashDigest(key, 'md5', 'base64', hashLength);
    };
    themes.forEach(t => {
      const transformedExp = t === 'default' ? exp : exp.replace(/(@(\w|-|_)+Color(\w|-|_)*\b)(?!=:)/g, `$1__${t}`);
      declarations[t].push(`--${hash}:${transformedExp};`);
    })
    result += source.substring(lastIndex, start);
    result += `var(--${hash})`;
    lastIndex = end;
  });
  result += source.substring(lastIndex);
  const declarationsStrings = themes.map(t => `${`${t === 'default' ? '&,' : ''}${useGlobal ? `&:global(.${t})` : `&.${t}`}`}{${declarations[t].join('')}}`);
  result = `${rootSelector}{${declarationsStrings.join('\n')}}` + result;
  return result;
}

function findExpPositions(source) {
  const regex = /(saturate|desaturate|lighten|darken|fadein|fadeout|fade|spin|mix|tint|shade|greyscale|contrast|alpha)\s*\(|@(\w|-|_)+Color(\w|-|_)*\b(?!=:)/g;
  const arr = [];
  while (true) {
    const result = regex.exec(source);
    if (!result) {
      break;
    }
    if (result[0][0] === '@') {
      arr.push([result.index, regex.lastIndex]);
    } else {
      const start = result.index;
      let hasVariable = false;
      let levels = 0;
      while (true) {
        const char = source[regex.lastIndex++];
        if (char === undefined) {
          hasVariable = false;
          break;
        }
        if (char === '@') {
          hasVariable = true;
        } else if (char === '(') {
          levels++;
        } else if (char === ')') {
          if (levels === 0) {
            if (hasVariable) {
              arr.push([start, regex.lastIndex, source.substring(start, regex.lastIndex)]);
            }
            break;
          }
          levels--;
        }
      }
    }
  }
  return arr;
}