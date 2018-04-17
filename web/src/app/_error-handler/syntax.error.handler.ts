import {HttpErrorHandler} from "./http.error.handler.interface";

export class SyntaxErrorHandler implements HttpErrorHandler {

  acceptable(error: any) {
    if (error.error.error instanceof SyntaxError) {
      return true;
    } else {
      return false;
    }
  }

  handle(error: any) {
    console.log("syntaxError")
  }
}
