import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

@Injectable()
export class ConfigService {

  getBackUrl(): string {
    if (environment['backUrl']) {
      return environment['backUrl'];
    } else {
      return "http://localhost:9000";
    }
  }
}
