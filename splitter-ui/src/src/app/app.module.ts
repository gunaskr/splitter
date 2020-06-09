import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ServiceWorkerModule } from '@angular/service-worker';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { environment } from '@env/environment';
import { CoreModule } from '@core';
import { SharedModule } from '@shared';
import { AuthModule } from '@app/auth';
import { HomeModule } from './home/home.module';
import { ShellModule } from './shell/shell.module';
import { AboutModule } from './about/about.module';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BASE_PATH as BASE_PATH_USER_MANAGEMENT } from './user-management-client/variables';
import { ApiModule as ApiModuleUserManagement } from './user-management-client/api.module';
import { BASE_PATH as BASE_PATH_TRANSACTION_MANAGEMENT } from './transaction-management-client/variables';
import { ApiModule as ApiModuleTransactionManagement } from './transaction-management-client/api.module';
import { RoommateModule } from './roommate/roommate.module';
import { TransactionModule } from './transaction/transaction.module';

@NgModule({
  imports: [
    BrowserModule,
    ServiceWorkerModule.register('./ngsw-worker.js', { enabled: environment.production }),
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot(),
    NgbModule,
    CoreModule,
    SharedModule,
    ShellModule,
    HomeModule,
    AboutModule,
    RoommateModule,
    TransactionModule,
    AuthModule,
    ApiModuleUserManagement,
    ApiModuleTransactionManagement,
    AppRoutingModule, // must be imported as the last module as it contains the fallback route
  ],
  declarations: [AppComponent],
  providers: [
    {
      provide: BASE_PATH_USER_MANAGEMENT,
      useValue: 'user-management',
    },
    {
      provide: BASE_PATH_TRANSACTION_MANAGEMENT,
      useValue: 'transaction-management',
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
