import {Injectable} from "@angular/core";
import {UserService} from "./user.service";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Category, Stuff} from "../_models";
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

  public addStuff(stuff: Stuff, categories: number[] = []): Observable<Stuff> {
    return this.http.post<Stuff>(this.config.getBackUrl() + "/rest/stuff", {"stuff": stuff, "categories": categories});
  }

  public delete(id): Observable<void> {
    return this.http.delete<void>(this.config.getBackUrl() + '/rest/stuff/' + id);
  }

  //TODO: move to CategoryService
  public getCategories(searchString): Observable<Set<Category>> {
    return this.http.get<Set<Category>>(this.config.getBackUrl() + '/rest/stuff/category/names?startsFrom=' + searchString);
  }

  public addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(
      this.config.getBackUrl() + '/rest/stuff/category',
      null,
      {params: {"categoryName": category.name}}
    );
  }

}
