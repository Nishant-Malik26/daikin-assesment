import type {
  ApiErrorResponse,
  BuildingResponse,
  CommonRoomType,
  CreateApartmentRequest,
  CreateCommonRoomRequest,
  TemperatureBody,
  UpdateApartmentRequest,
  UpdateCommonRoomRequest,
} from "../types/building";

const BASE_API_URL = process.env.BASE_API_URL || "http://localhost:8080";

function getErrorMessage(status: number, errorBody?: ApiErrorResponse) {
  if (status >= 500) {
    return "Internal Server Error";
  }

  if (status === 404) {
    return errorBody?.message || "The requested resource was not found.";
  }

  if (status === 400) {
    return (
      errorBody?.message || "The request was invalid. Please check your input."
    );
  }

  return (
    errorBody?.message || "Something went wrong while processing your request."
  );
}

async function parseResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    let errorBody: ApiErrorResponse | undefined;

    try {
      errorBody = (await response.json()) as ApiErrorResponse;
    } catch {
      errorBody = undefined;
    }

    throw new Error(getErrorMessage(response.status, errorBody));
  }

  if (response.status === 204) {
    return undefined as T;
  }

  const contentLength = response.headers.get("content-length");
  const contentType = response.headers.get("content-type");

  if (contentLength === "0") {
    return undefined as T;
  }

  if (!contentType || !contentType.includes("application/json")) {
    return undefined as T;
  }

  const text = await response.text();

  if (!text.trim()) {
    return undefined as T;
  }

  return JSON.parse(text) as T;
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${BASE_API_URL}${path}`, {
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...(init?.headers ?? {}),
    },
    cache: "no-store",
  });

  return parseResponse<T>(response);
}

export function getBuilding() {
  return request<BuildingResponse>("/buildings");
}

export function updateBuildingTemperature(payload: TemperatureBody) {
  return request<BuildingResponse>("/buildings/temperature", {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export function createApartment(payload: CreateApartmentRequest) {
  return request("/apartments", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function deleteApartment(id: number) {
  return request<void>(`/apartments/${id}`, {
    method: "DELETE",
  });
}

export function createCommonRoom(payload: CreateCommonRoomRequest) {
  return request("/commonRooms", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function deleteCommonRoom(id: number) {
  return request<void>(`/commonRooms/${id}`, {
    method: "DELETE",
  });
}

export function updateApartmentRoom(
  id: number,
  payload: UpdateApartmentRequest,
) {
  return request<void>(`/rooms/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export function updateCommonRoom(id: number, payload: UpdateCommonRoomRequest) {
  return request<void>(`/rooms/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export const COMMON_ROOM_OPTIONS: CommonRoomType[] = [
  "GYM",
  "LIBRARY",
  "LAUNDRY",
];
