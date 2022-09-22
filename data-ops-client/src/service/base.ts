import { extend, ResponseError } from 'umi-request';
import { message } from 'antd';


export interface IOptions{
  method?: "get" | 'post' | 'put' | 'delete';
  mock?: boolean;
}

// TODO 
const codeMessage:{[errorCode:number]:string} = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

const mockUrl = 'https://yapi.alibaba.com/mock/1000160';
export const baseURL = location.host.indexOf('localhost') > -1 ? mockUrl : '/';

const errorHandler = (error: ResponseError) => {
  const { response } = error;
  const errortext = codeMessage[response.status] || response.statusText;
  const { status } = response;
  message.error(`${status}: ${errortext}`)
};

const request = extend({
  errorHandler,
  // prefix: '/api',
  credentials: 'include', // 默认请求是否带上cookie
});

request.interceptors.request.use((url, options) => {
  return {
    options: {
      ...options,
    },
  };
});

// request.interceptors.response.use(async response => {
//   const res = await response.clone().json();
//   console.log(response)
//   const { code, message } = res;
//   if (code !== 0) {
//     console.log('error', res);
//     message.error(`${code}: ${message}`)
//     return response;
//   }
//   return res;
// });

export default function createRequest<P = void, R = {}>(url:string, options:IOptions){
  const {method = 'get', mock = false} = options;
  const _baseURL = mock ? mockUrl : baseURL;

  return function(params: P ){
    const paramsData: {[key:string]:any} = params;
    const paramsInUrl: string[] = [];
    const _url = url.replace(/:(.+?)\b/, (_, name:string) => {
      const value = paramsData[name];
      paramsInUrl.push(name);
      return `${value}`;
    });

    if (paramsInUrl.length) {
      paramsInUrl.forEach((name) => {
        delete paramsData[name];
      });
    }

    return new Promise<R>((resolve, reject) => {
      request[method](`${_baseURL}${_url}`,{data: params})
      .then(res=>{
        const {success, errorCode, errorMessage, data} = res
        if(!success){
          message.error(`${errorCode}: ${errorMessage}`)
        }
        resolve(data)
      })
      .catch(error=>{
        reject(error)
      })
    }) 
  } 
}
