import {HttpErrorHandler} from "./http.error.handler.interface";

export class DefaultHttpCodesErrorHandler implements HttpErrorHandler {
  acceptable(error: any): boolean {
    return true;
  }

  handle(error: any): void {
    console.log("defaultHttpError")
  }

}
