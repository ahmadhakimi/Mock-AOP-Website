import { Company } from './company';
import { Terminal } from './staff';
import { Role } from './staff';

export interface Pass {
  id: string;
  company: Company;
  purpose: string;
  applicationNo: string;
  applicationName: string;
  icNo: string;
  passportNo: string;
  terminal: Terminal;
  areas: Set<string>;
  applicantType: ApplicantType;
  status: ApplicationStatus;
  submittedBy: string;
  submittedDate: Date;
  role: Role;
  passAttachmentFile: PassAttachmentEntity;
}

export enum ApplicationStatus {
  SUBMITTED = 'SUBMITTED',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
}

export enum ApplicantType {
  MALAYSIAN = 'MALAYSIAN',
  NON_MALAYSIAN = 'NON MALAYSIAN',
}

export interface PassAttachmentEntity {
  id: string;
  name: string;
  type: string;
}
