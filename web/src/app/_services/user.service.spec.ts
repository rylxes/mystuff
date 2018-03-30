import {UserService} from "./user.service";
import {TestBed} from "@angular/core/testing";

import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {ConfigService} from "./config.service";


describe('UserService', () => {

  let userService: UserService;
  let httpMock: HttpTestingController;
  let configService: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserService, ConfigService],
      imports: [HttpClientTestingModule]
    });

    httpMock = TestBed.get(HttpTestingController);
    userService = TestBed.get(UserService);
    configService = TestBed.get(ConfigService);
  });

  it('create should pass and return correct user', () => {
    const testUser: any = {username: "testName", password: "testPassword"};

    userService.create(testUser).subscribe(user => {
        expect(user).toEqual(testUser);
      }
    );

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/user/register');
    expect(req.request.method).toEqual('POST');
    req.flush(testUser)

    httpMock.verify();
  });



});
