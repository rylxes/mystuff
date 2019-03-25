import {Component, OnInit, ViewChild} from "@angular/core";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {StuffService} from "../_services/stuff.service";
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {AddCategoryComponent} from "../add-category/add-category.component";
import {Router} from "@angular/router";

@Component({
  selector: 'add-stuff',
  templateUrl: './add-stuff.component.html',
  styleUrls: ['./add-stuff.component.css']
})
export class AddStuffComponent implements OnInit {
  @ViewChild(AddCategoryComponent)
  private addCategoryComponent: AddCategoryComponent;

  addStuff: FormGroup;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private stuffService: StuffService,
              private router: Router) {
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
    setTimeout(() => {
      this.stuffService.addStuff(this.addStuff.value, this.getCategoryIds()).subscribe(
        stuff => {
          console.log(stuff);
          this.router.navigate(['/stuff/' + stuff.id]);
        }
      );
    }, 1000);
  }

  ngOnInit() {
  }

  private getCategoryIds(): number[] {
    return this.addCategoryComponent.getCategories().map(value => value.id)
  }
}



