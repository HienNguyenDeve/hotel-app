import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule, IconDefinition } from '@fortawesome/angular-fontawesome';
import { faPlus, faSearch, faEdit, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { RoomDetailsComponent } from './room-details/room-details.component';

@Component({
  selector: 'app-room-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FontAwesomeModule, RoomDetailsComponent],
  templateUrl: './room-list.component.html',
  styleUrl: './room-list.component.css'
})
export class RoomListComponent implements OnInit{
  public isShowDetails: boolean = false;
  public selectedItem!: any;

  public faPlus: IconDefinition = faPlus;
  public faSearch: IconDefinition = faSearch;
  public faEdit: IconDefinition = faEdit;
  public faTrashCan: IconDefinition = faTrashCan;

  public searchForm!: FormGroup;
  public data: any[] = [];

  private apiUrl: string = "http://localhost:8080/api/v1/rooms/search"

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.createForm();
    this.search();
  }

  private search(): void {
    this.apiUrl = `http://localhost:8080/api/v1/rooms/search?page=0&size=10`;
    this.httpClient.get(this.apiUrl).subscribe((data: any) => {
      this.data = data._embedded.roomDTOList;
    });
  }

  private createForm(): void {
    this.searchForm = new FormGroup({
      keyword: new FormControl('', [Validators.maxLength(255)])
    });
  }

  public onSubmit() {
    if (this.searchForm.invalid) {
      return;
    }
    this.apiUrl = `http://localhost:8080/api/v1/rooms/searchByRoomNumber?keyword=${this.searchForm.value.keyword}`;
    this.search();
  }

  public onDelete(id: string): void {
    this.httpClient.delete(`${this.apiUrl}/${id}`).subscribe((result: any) => {
      if (result) {
        this.search();
        console.log('Delete success');
      } else {
        console.log('Delete failed');
      }
    });
  }

  public onEdit(id: string): void {
    this.isShowDetails = false;
    this.selectedItem = this.data.find((item) => item.id === id);
    this.isShowDetails = true;
  }

  public onCreate(): void {
    this.isShowDetails = true;
    this.selectedItem = null;
  }

  public cancelDetail(): void {
    this.isShowDetails = false;
    this.search();
  }
}
