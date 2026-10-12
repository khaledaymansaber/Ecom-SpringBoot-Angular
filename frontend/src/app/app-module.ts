import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { CoreModule } from './core/core-module';
import { HomeModule } from './home/home-module';

import { loaderInterceptor } from './core/Interceptor/loader-interceptor';
import { jwtInterceptor } from './core/Interceptor/jwt-interceptor';
import { errorInterceptor } from './core/Interceptor/error-interceptor';

@NgModule({
  declarations: [App],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HomeModule,
    NgxSpinnerModule,
    AppRoutingModule,
    CoreModule,
    ToastrModule.forRoot({
      closeButton: true,
      positionClass: 'toast-top-right',
      countDuplicates: true,
      timeOut: 2000,
      progressBar: true,
    })
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    provideClientHydration(),
    provideHttpClient(withInterceptors([loaderInterceptor, jwtInterceptor, errorInterceptor]))
  ],
  bootstrap: [App],
})
export class AppModule {}
