import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions, Response} from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
  constructor(private http: Http) {
  }

  login(username: string, password: string) {
    let headers = new Headers();
    headers.append("Content-Type", 'application/json');
    let requestOptions = new RequestOptions({headers: headers});

    return this.http.post('http://127.0.0.1:9000/rest/token/new', {
      username: username,
      password: password
    }, requestOptions)
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let token = response.text();
        if (token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', token);
        }
      });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }
}
