import {Component, OnInit} from '@angular/core';
import {Entry} from './entry';
import {EntryService} from './entry.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'feedbacksystemapp';
  public entries: Entry[];
  public deleteEntry: Entry;
  public categoryArray: string[] = [];

  closeResult: string;
  constructor(private entryService: EntryService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.getEntries();
  }

  public getEntries(): void {
    this.entryService.getEntries().subscribe(
      (response: Entry[]) => {
        this.entries = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public openDelete(targetModal, entry: Entry): void{
    this.deleteEntry = entry;
    this.modalService.open(targetModal);
  }

  public onDelete(entryId: number): void{
    this.entryService.deleteEntry(entryId)
      .subscribe(() => {
        this.ngOnInit();
        this.modalService.dismissAll();
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
    });
  }

  public onAddEntry(f: NgForm): void {
    this.modalService.dismissAll();
    f.value.categories = this.categoryArray;
    this.entryService.addEntry(f.value)
      .subscribe((response: Entry) => {
        console.log(response);
        this.getEntries();
      },
        (error: HttpErrorResponse) => {
        alert(error.message);
        });
    this.categoryArray = [];
  }

  open(content): void {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  public searchEntries(key: string): void {
    const results: Entry[] = [];
    for (const entry of this.entries) {
      if (entry.name.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        (entry.email.toLowerCase().indexOf(key.toLowerCase()) !== -1) ||
        (entry.text.toLowerCase().indexOf(key.toLowerCase()) !== -1) ||
        (entry.id.toString().indexOf(key.toLowerCase()) !== -1) ||
        this.categoryMatch(entry, key)) {
        results.push(entry);
      }
    }
    this.entries = results;
    if (results.length === 0 || !key){
      this.getEntries();
    }
  }

  private categoryMatch(entry: Entry, key: string): boolean{
    for (const category of entry.categories){
      if (category.toLowerCase().indexOf(key.toLowerCase()) !== -1){
        return true;
      }
    }
    return false;
  }

  public onChange(event): void{
    this.categoryArray = event;
  }
}
