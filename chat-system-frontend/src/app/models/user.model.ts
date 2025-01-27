import { DatePipe } from "@angular/common";

export interface User {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    createdAt?: DatePipe;
    updatedAt?: DatePipe;
}