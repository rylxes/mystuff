import {ErrorHandler, Inject, Injectable, Injector, NgZone} from '@angular/core';
import {HANDLERS, HttpErrorHandler} from "./http.error.handler.interface";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  constructor(@Inject(HANDLERS) private handlers: HttpErrorHandler[]) {
  }

  handleError(error: any) {
    for (let errorHandler of this.handlers) {
      if (errorHandler.acceptable(error)) {
        errorHandler.handle(error);
        break;
      }
    }
    console.log(error);
  }
}

