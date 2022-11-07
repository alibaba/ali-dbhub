import createRequest from "./base";
const testService = createRequest<void, void>('', { errorLevel: false });

export default {
  testService
}