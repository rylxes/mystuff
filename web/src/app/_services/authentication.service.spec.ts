import {TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {ConfigService} from "./config.service";
import {StuffService} from "./stuff.service";
import {AuthenticationService} from "./authentication.service";
import {TokenService} from "./token.service";


describe('AuthenticationService', () => {

  let authService: AuthenticationService;
  let httpMock: HttpTestingController;
  let configService: ConfigService;
  let tokenService: TokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TokenService, ConfigService, AuthenticationService],
      imports: [HttpClientTestingModule]
    });

    httpMock = TestBed.get(HttpTestingController);
    authService = TestBed.get(AuthenticationService);
    configService = TestBed.get(ConfigService);
    tokenService = TestBed.get(TokenService);

  });

  it('#login should sent request with correct data', () => {
    const testRequestBody: any = {username: 'testUser', password: 'testPassword'};
    const testUserName: string = 'testUser';
    const testPassword: string = 'testPassword';

    authService.login(testUserName, testPassword).subscribe(() => {});

    const req = httpMock.expectOne(configService.getBackUrl() + '/rest/token/new');
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(testRequestBody);
    httpMock.verify();
  });

  it('#logout should call tokenService.removeToken()', () => {
    const tokenSpy = jasmine.createSpyObj('TokenService', ['removeToken']);
    let authServiceWithSpy = new AuthenticationService(null, tokenSpy, null);

    authServiceWithSpy.logout();
    expect(tokenSpy.removeToken).toHaveBeenCalled();
    expect(tokenSpy.removeToken.calls.count()).toBe(1);

  });

  it('#isAuthorized should return expected value from tokenService', () => {
    const tokenSpy = jasmine.createSpyObj('TokenService', ['tokenExists']);
    tokenSpy.tokenExists.and.returnValue(true);
    let authServiceWithSpy = new AuthenticationService(null, tokenSpy, null);

    expect(authServiceWithSpy.isAuthorized()).toBeTruthy();
    expect(tokenSpy.tokenExists).toHaveBeenCalled();
    expect(tokenSpy.tokenExists.calls.count()).toBe(1)
  });


});

