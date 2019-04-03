import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map'
import {TokenService} from "./token.service";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "./config.service";

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient,
              private tokenService: TokenService,
              private config: ConfigService) {
  }

  login(username: string, password: string) {
    let body = {
      username: username,
      password: password
    };

    return this.http.post(this.config.getBackUrl() + 'rest/token/new', body, {responseType: 'text'});
  }

  logout() {
    this.tokenService.removeToken();
  }

  isAuthorized(): boolean {
    return this.tokenService.tokenExists();
  }
}
