import {Injectable} from "@angular/core";
import {UserService} from "./user.service";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Stuff} from "../_models";
import {ConfigService} from "./config.service";

@Injectable()
export class StuffService {

  constructor(private http: HttpClient,
              private userService: UserService,
              private config: ConfigService) {
  }

  public getStuff(id): Observable<Stuff> {
    return this.http.get<Stuff>(this.config.getBackUrl() + '/rest/stuff/' + id);
  }

  public getMyStuff(): Observable<Stuff[]> {
    return this.http.get<Stuff[]>(this.config.getBackUrl() + '/rest/stuff/list');
  }

  public addStuff(stuff: Stuff): Observable<Stuff> {
    return this.http.post<Stuff>(this.config.getBackUrl() + "/rest/stuff", stuff);
  }

  public delete(id): Observable<void> {
    return this.http.delete<void>(this.config.getBackUrl() + '/rest/stuff/' + id);
  }
}
