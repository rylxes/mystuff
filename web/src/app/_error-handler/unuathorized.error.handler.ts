import {HttpErrorHandler} from "./http.error.handler.interface";

export class UnauthorizedErrorHandler implements HttpErrorHandler {

  acceptable(error: any): boolean {

    if (error.status == 401) {
      return true;
    } else {
      return false;
    }
  }

  handle(error: any): void {
    console.log("unauthorizedError")
  }

}
