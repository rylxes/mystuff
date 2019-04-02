import {Injectable} from '@angular/core';

import {User} from '../_models';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {ConfigService} from "./config.service";

@Injectable()
export class UserService {
  constructor(private http: HttpClient,
              private config: ConfigService) {
  }

  // getAll() {
  //   return this.http.get('/api/users', this.jwt()).map((response: Response) => response.json());
  // }
  //
  // getById(id: number) {
  //   return this.http.get('/api/users/' + id, this.jwt()).map((response: Response) => response.json());
  // }

  create(user: User): Observable<User> {
    return this.http.post<User>(this.config.getBackUrl() + 'rest/user/register', user);
  }

  // update(user: User) {
  //   return this.http.put('/api/users/' + user.id, user, this.jwt()).map((response: Response) => response.json());
  // }
  //
  // delete(id: number) {
  //   return this.http.delete('/api/users/' + id, this.jwt()).map((response: Response) => response.json());
  // }

  // private helper methods
  // /**
  //  * @deprecated
  //  * @returns {RequestOptions}
  //  */
  // public jwt() {
  //   // create authorization header with jwt token
  //   let token = localStorage.getItem('token');
  //   if (token) {
  //     let headers = new Headers({'Authorization': 'Bearer ' + token});
  //     return new RequestOptions({headers: headers});
  //   }
  // }
}
