import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {CookieService} from "ngx-cookie-service";

@Injectable()
export class ConfigService {

  constructor(private cookieService: CookieService) {
  }

  getBackUrl(): string {
    if (this.cookieService.check('back_url')) {
      return this.cookieService.get('back_url');
    } else if (environment['backUrl']) {
      return environment['backUrl'];
    } else {
      return "http://localhost:9000";
    }
  }
}
