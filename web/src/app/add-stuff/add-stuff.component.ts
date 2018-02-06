import {Component, OnInit} from "@angular/core";
import {Stuff} from "../_models/Stuff";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {StuffService} from "../_services/stuff.service";

@Component({
  selector: 'add-stuff',
  templateUrl: './add-stuff.component.html',
  styleUrls: ['./add-stuff.component.css']
})
export class AddStuffComponent implements OnInit {

  addStuff: FormGroup;
  model: Stuff = new Stuff(0, "", "", []);

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private stuffService: StuffService) {
    this.stuffFormInit();
  }

  stuffFormInit() {
    this.addStuff = this.fb.group({
      name: '',
      description: '',
      categories: this.fb.array([])
    });
  }

  save() {
    // let headers = new Headers();
    // headers.append("Content-Type", 'application/json');
    // headers.append("X-Auth-Token", "Bearer " + localStorage.getItem("token"));
    // let requestOptions = new RequestOptions({headers: headers});
    // this.userService.create(this.addStuff.value).subscribe(stuff => {
    //   console.log(stuff);
    //
    // });
    // this.http.post(this.restService.getUrl() + "/rest/stuff", this.addStuff.value, this.restService.defaultHeaders()).subscribe(stuff => {
    this.stuffService.addStuff(this.addStuff.value).subscribe(stuff => {
      //TODO: update data
      console.log(stuff);
    });
  }

  ngOnInit() {
  }

}
