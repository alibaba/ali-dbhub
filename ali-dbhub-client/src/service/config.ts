import createRequest from "./base";
const getSystemConfig = createRequest<{code:string}, {code:string,content:string}>('/api/config/system_config/:code', { errorLevel: false });
const setSystemConfig = createRequest<{code:string, content:string}, void>('/api/config/system_config', { errorLevel: false,method:'post' });

export default {    
  getSystemConfig,
  setSystemConfig
}