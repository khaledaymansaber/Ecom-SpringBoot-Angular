import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ProfileService } from '../../core/Services/profile.service';
import { IUserProfile } from '../../shared/Models/UserProfile';

@Component({
  selector: 'app-profile',
  standalone: false,
  styleUrl: './profile.scss',
  templateUrl: './profile.html',
})
export class Profile implements OnInit {
  profileForm!: FormGroup;
  addressForm!: FormGroup;
  profile: IUserProfile | null = null;
  loading = true;

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    private toastr: ToastrService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.initForms();
    this.loadProfileData();
  }

  initForms() {
    this.profileForm = this.fb.group({
      displayName: ['', [Validators.required, Validators.minLength(2)]],
      email: [{ value: '', disabled: true }]
    });

    this.addressForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zipCode: ['', Validators.required]
    });
  }

  loadProfileData() {
    this.loading = true;
    this.profileService.getProfile().subscribe({
      next: p => {
        this.profile = p;
        this.profileForm.patchValue({
          displayName: p.displayName,
          email: p.email
        });
        if (p.address) {
          this.addressForm.patchValue(p.address);
        }
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Failed to load profile', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  saveProfile() {
    if (this.profileForm.invalid) return;

    this.profileService.updateProfile({ displayName: this.profileForm.get('displayName')?.value }).subscribe({
      next: updated => {
        this.toastr.success('Profile updated successfully!', 'Success');
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
  }

  saveAddress() {
    if (this.addressForm.invalid) {
      this.addressForm.markAllAsTouched();
      return;
    }

    this.profileService.updateAddress(this.addressForm.value).subscribe({
      next: () => {
        this.toastr.success('Default shipping address saved!', 'Success');
        this.cdr.detectChanges();
      },
      error: err => console.error(err)
    });
  }
}
