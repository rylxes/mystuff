import {HttpErrorHandler} from "./http.error.handler.interface";

export class TimeoutErrorHandler implements HttpErrorHandler {

  acceptable(error: any): boolean {
    if (error.status == 0) {
      return true;
    } else {
      return false;
    }
  }
  handle(error: any): void {
    console.log("timeoutError")
  }


}
