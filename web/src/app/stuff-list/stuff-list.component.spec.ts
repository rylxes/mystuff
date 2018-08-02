import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StuffListComponent} from './stuff-list.component';

xdescribe('StuffListComponent', () => {
  let component: StuffListComponent;
  let fixture: ComponentFixture<StuffListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StuffListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StuffListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
