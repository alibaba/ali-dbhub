import createRequest from "./base";
const testService = createRequest<void, void>('/api/system', { errorLevel: false });

export default {
  testService
}