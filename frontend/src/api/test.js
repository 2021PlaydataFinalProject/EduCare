import { tests } from "./index";

function fetchTest() {
  return tests.get("testNum");
}

function deleteTest(testNum) {
  return tests.delete(testNum);
}
export { fetchTest, deleteTest };
