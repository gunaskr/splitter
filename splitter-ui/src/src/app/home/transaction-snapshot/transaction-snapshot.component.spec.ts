import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionSnapshotComponent } from './transaction-snapshot.component';

describe('TransactionSnapshotComponent', () => {
  let component: TransactionSnapshotComponent;
  let fixture: ComponentFixture<TransactionSnapshotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionSnapshotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionSnapshotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
