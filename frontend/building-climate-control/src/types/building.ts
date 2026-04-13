export type AcMode = "HEATING" | "COOLING" | "IDLE";
export type CommonRoomType = "GYM" | "LIBRARY" | "LAUNDRY";

export interface RoomResponse {
  id: number;
  currentTemperature: number;
  acMode: AcMode;
}

export interface ApartmentResponse {
  ownersName: string;
  unitNumber: string;
  room: RoomResponse;
}

export interface CommonRoomResponse {
  roomType: CommonRoomType;
  room: RoomResponse;
}

export interface BuildingResponse {
  id: number;
  apartmentResponses: ApartmentResponse[];
  rooms: CommonRoomResponse[];
  requestedTemperature: number;
}

export interface TemperatureBody {
  temperature: number;
}

export interface CreateApartmentRequest {
  ownersName: string;
  unitNumber: string;
}

export interface CreateCommonRoomRequest {
  roomType: CommonRoomType;
}

export interface UpdateApartmentRequest {
  ownersName: string;
  unitNumber: string;
}

export interface UpdateCommonRoomRequest {
  roomType: CommonRoomType;
}

export interface ApiErrorResponse {
  timestamp?: string;
  status?: number;
  error?: string;
  message?: string;
}
