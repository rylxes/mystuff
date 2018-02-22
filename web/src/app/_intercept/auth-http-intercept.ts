import {HttpInterceptor} from "@angular/common/http";
import {HttpHandler} from "@angular/common/http/src/backend";
import {Observable} from "rxjs/Observable";
import {HttpEvent} from "@angular/common/http/src/response";
import {HttpRequest} from "@angular/common/http/src/request";
import {TokenService} from "../_services/token.service";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthHttpIntercept implements HttpInterceptor {

  constructor(private tokenService: TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.tokenService.tokenExists()) {
      req = req.clone({
        headers: req.headers.append('Authorization', 'Bearer ' + this.tokenService.getToken())
      });
    }
    return next.handle(req);
  }
}
