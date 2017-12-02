import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {UserService} from "./user.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class StuffService {

  constructor(private http: Http, private userService: UserService) {
  }

  public getStuff(id): Observable<Response> {
    return this.http.get('http://127.0.0.1:9000/rest/stuff/' + id, this.userService.jwt());
  }

  public getMyStuff(): Observable<Response> {
    return this.http.get('http://127.0.0.1:9000/rest/stuff/list', this.userService.jwt());
  }

  public delete(id, onFinish: () => void) {
    let options = this.userService.jwt();
    options.headers.set('id', id);
    return this.http.delete('http://127.0.0.1:9000/rest/stuff', options).subscribe(res => onFinish());
  }
}
