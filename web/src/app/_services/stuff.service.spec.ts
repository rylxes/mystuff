import {TestBed} from "@angular/core/testing";

import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {ConfigService} from "./config.service";
import {StuffService} from "./stuff.service";
import {UserService} from "./user.service";
import {Stuff} from "../_models/Stuff";


describe('StuffService', () => {

  let stuffService: StuffService;
  let httpMock: HttpTestingController;
  let configService: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StuffService, ConfigService, UserService],
      imports: [HttpClientTestingModule]
    });

    httpMock = TestBed.get(HttpTestingController);
    stuffService = TestBed.get(StuffService);
    configService = TestBed.get(ConfigService);
  });

  it('#getStuff should return expected Stuff', () => {
    const testStuff: Stuff = {id: 1, name: 'testUser', description: 'TestDescription', categories: []};
    const testId: number = 1;

    stuffService.getStuff(testId).subscribe(stuff => {
        expect(stuff).toEqual(jasmine.objectContaining(testStuff));
      }
    );

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/' + testId);
    expect(req.request.method).toEqual('GET');
    req.flush(testStuff);
    httpMock.verify();
  });

  it('#getMyStuff should return expected list of Stuffs', () => {
    const testStuffArray: Stuff[] =
      [{id: 1, name: 'testUser1', description: 'TestDescription1', categories: []},
        {id: 2, name: 'testUser2', description: 'TestDescription2', categories: []}];

    stuffService.getMyStuff().subscribe(stuff => {
        expect(stuff).toEqual(testStuffArray);
      }
    );

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/list');
    expect(req.request.method).toEqual('GET');
    req.flush(testStuffArray);
    httpMock.verify();
  });


  it('#addStuff should save and return correct stuff', () => {
    const testStuff: Stuff = {id: 1, name: 'testUser', description: 'TestDescription', categories: []};

    stuffService.addStuff(testStuff).subscribe(stuff => {
        expect(stuff).toEqual(jasmine.objectContaining(testStuff));
      }
    );

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff');
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(testStuff);
    req.flush(testStuff);
    httpMock.verify();

  });

  it('#delete should works correct', () => {

    const testId: number = 1;
    stuffService.delete(testId).subscribe(() => {
    });

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/stuff/' + testId);
    expect(req.request.method).toEqual('DELETE');
    httpMock.verify();
  });
});
