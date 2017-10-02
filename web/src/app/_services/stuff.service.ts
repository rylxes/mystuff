import {Stuff} from "../_models/Stuff";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class StuffService {

  constructor(private http: HttpClient) {
  }

  public getStuff(id): Stuff {

    return null;
  }
}
