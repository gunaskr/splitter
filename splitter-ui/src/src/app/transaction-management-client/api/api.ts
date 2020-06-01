export * from './categoryController.service';
import { CategoryControllerService } from './categoryController.service';
export * from './eventController.service';
import { EventControllerService } from './eventController.service';
export * from './transactionController.service';
import { TransactionControllerService } from './transactionController.service';
export const APIS = [CategoryControllerService, EventControllerService, TransactionControllerService];
