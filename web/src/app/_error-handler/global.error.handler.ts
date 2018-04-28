import {ErrorHandler, Inject, Injectable, Injector, NgZone} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {TokenService} from "../_services/token.service";
import {AlertService} from "../_services/alert.service";

import {NotificationsService} from 'angular2-notifications';
import {HANDLERS, HttpErrorHandler} from "./http.error.handler.interface";


@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(@Inject(HANDLERS) private handlers: HttpErrorHandler[]) {}

  handleError(error: any) {
    for (let errorHandler of this.handlers) {
      if (errorHandler.acceptable(error)) {
        errorHandler.handle(error);
      }
    }
  }
}

