import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ProfileRoutingModule } from './profile-routing-module';
import { Profile } from './profile/profile';

@NgModule({
  declarations: [
    Profile
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ProfileRoutingModule
  ]
})
export class ProfileModule { }
