import {ErrorHandler, Injectable} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {TokenService} from "../_services/token.service";


@Injectable()
export class ErrorsHandler implements ErrorHandler {

  constructor(private tokenService: TokenService) {
  }

  handleError(error: any) {
    if (error.status == 401) {
      this.tokenService.removeToken();
    }
    console.log(error)
  }

}

