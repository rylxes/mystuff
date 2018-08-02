import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ConfigService {

  constructor(private http: HttpClient) {}

  public init() {
    return new Promise((resolve, reject) => {
      this.http.get("/config.json").subscribe(config => {
        this.replace(config, environment);
        console.log(environment);
        resolve();
      }, error => reject(error));
    });
  }

  private replace(config, env) {
    for (let key in config) {
      let element = config[key];
      if (typeof element !== "object") {
        env[key] = element;
      } else {
        env[key] = this.replace(element, env[key]);
        console.log(env);
      }
    }
    return env;
  }

  getBackUrl(): string {
    return environment.backUrl;
  }
}
