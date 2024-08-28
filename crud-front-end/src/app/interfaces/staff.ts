export interface Staff {
  id: string;
  fullName: string;
  userName: string;
  email: string;
  password: string;

  terminal?: string;
  role?: string;
  status?: string;
  createdBy: string;
  updatedBy: string;
  deleted: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export enum Terminal {
  KLIA = 'KLIA',
  KLIA2 = 'KLIA2',
  PENANG = 'PENANG',
  FCZ = 'FCZ',
}

export enum Status {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  LOCKED = 'LOCKED',
}

export enum Role {
  ADMIN = 'ADMIN',
  USER = 'USER',
}
