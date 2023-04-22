import createRequest from "./base";
const testService = createRequest<void, void>('/api/system', { errorLevel: false });
const systemStop = createRequest<void, void>('/api/system/stop', { errorLevel: false, method: 'post' });

export default {
  testService,
  systemStop
}