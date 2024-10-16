import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewShelfComponent } from './new-shelf.component';

describe('NewShelfComponent', () => {
  let component: NewShelfComponent;
  let fixture: ComponentFixture<NewShelfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewShelfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewShelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
