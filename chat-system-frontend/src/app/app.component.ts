import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConnectionsComponent } from './components/connections/connections.component';
import { MessagesComponent } from './components/messages/messages.component';
import { SettingsComponent } from './components/settings/settings.component';
import { UserService } from './services/users.service';
import { USER_ID } from './constants/constants';
import { User } from './models/user.model';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [CommonModule, ConnectionsComponent, MessagesComponent, SettingsComponent]
})
export class AppComponent implements OnInit {
  selectedType: 'channel' | 'friend' | null = null;
  selectedId: number | null = null;

  userId = USER_ID;

  user: User | null = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadLoggedInUserDetails();
  }

  loadLoggedInUserDetails(): void {
    this.userService.getUserDetails(this.userId).subscribe((response) => this.user = response);
  }

  onSelection(type: 'channel' | 'friend', id: number): void {
    this.selectedType = type;
    this.selectedId = id;
    // console.log(`Selected ${type}:`, id);
  }

  onRefreshChannels(): void {
    this.selectedType = null;
    this.selectedId = null;
  }
}
