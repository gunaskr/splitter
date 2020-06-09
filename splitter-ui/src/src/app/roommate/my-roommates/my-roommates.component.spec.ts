import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyRoommatesComponent } from './my-roommates.component';

describe('MyRoommatesComponent', () => {
  let component: MyRoommatesComponent;
  let fixture: ComponentFixture<MyRoommatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyRoommatesComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyRoommatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
