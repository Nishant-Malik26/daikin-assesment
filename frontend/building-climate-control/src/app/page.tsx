"use client";

import { useEffect, useState } from "react";
import {
  Alert,
  Button,
  Col,
  Row,
  Space,
  Spin,
  Typography,
  message,
  Layout,
} from "antd";
import {
  createApartment,
  createCommonRoom,
  deleteApartment,
  deleteCommonRoom,
  getBuilding,
  updateApartmentRoom,
  updateCommonRoom,
  updateBuildingTemperature,
} from "../apiHelpers/api";
import type {
  ApartmentResponse,
  BuildingResponse,
  CommonRoomResponse,
  CreateApartmentRequest,
  CreateCommonRoomRequest,
  UpdateApartmentRequest,
  UpdateCommonRoomRequest,
} from "../types/building";
import BuildingHeader from "../components/BuildingHeader";
import TemperatureControl from "../components/TemperatureControl";
import RoomCard from "../components/RoomCard";
import AddRoomModal from "../components/AddRoomModal";
import EditRoomModal from "../components/EditRoomModal";

const { Title, Paragraph } = Typography;
const { Header, Content } = Layout;
type EditTarget =
  | {
      kind: "apartment";
      data: ApartmentResponse;
    }
  | {
      kind: "commonRoom";
      data: CommonRoomResponse;
    }
  | null;

export default function Home() {
  const [building, setBuilding] = useState<BuildingResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [addRoomOpen, setAddRoomOpen] = useState(false);
  const [createRoomLoading, setCreateRoomLoading] = useState(false);
  const [editTarget, setEditTarget] = useState<EditTarget>(null);
  const [editLoading, setEditLoading] = useState(false);
  const [temperatureLoading, setTemperatureLoading] = useState(false);
  const [deletingRoomId, setDeletingRoomId] = useState<number | null>(null);
  const [messageApi, contextHolder] = message.useMessage();

  async function loadBuilding() {
    try {
      setLoading(true);
      setErrorMessage(null);
      const data = await getBuilding();
      setBuilding(data);
    } catch (error) {
      setErrorMessage(
        error instanceof Error ? error.message : "Failed to load building data",
      );
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    void loadBuilding();
  }, []);

  setTimeout(() => {
    //void loadBuilding();
  }, 5000);

  async function handleCreateApartment(payload: CreateApartmentRequest) {
    try {
      setCreateRoomLoading(true);
      await createApartment(payload);
      setAddRoomOpen(false);
      messageApi.success("Apartment created successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to create apartment",
      );
    } finally {
      setCreateRoomLoading(false);
    }
  }

  async function handleCreateCommonRoom(payload: CreateCommonRoomRequest) {
    try {
      setCreateRoomLoading(true);
      await createCommonRoom(payload);
      setAddRoomOpen(false);
      messageApi.success("Common room created successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to create common room",
      );
    } finally {
      setCreateRoomLoading(false);
    }
  }

  async function handleUpdateApartment(
    roomId: number,
    payload: UpdateApartmentRequest,
  ) {
    try {
      setEditLoading(true);
      await updateApartmentRoom(roomId, payload);
      setEditTarget(null);
      messageApi.success("Apartment updated successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to update apartment",
      );
    } finally {
      setEditLoading(false);
    }
  }

  async function handleUpdateCommonRoom(
    roomId: number,
    payload: UpdateCommonRoomRequest,
  ) {
    try {
      setEditLoading(true);
      await updateCommonRoom(roomId, payload);
      setEditTarget(null);
      messageApi.success("Common room updated successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to update common room",
      );
    } finally {
      setEditLoading(false);
    }
  }

  async function handleDeleteApartment(roomId: number) {
    try {
      setDeletingRoomId(roomId);
      await deleteApartment(roomId);
      messageApi.success("Apartment deleted successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to delete apartment",
      );
    } finally {
      setDeletingRoomId(null);
    }
  }

  async function handleDeleteCommonRoom(roomId: number) {
    try {
      setDeletingRoomId(roomId);
      await deleteCommonRoom(roomId);
      messageApi.success("Common room deleted successfully");
      await loadBuilding();
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to delete common room",
      );
    } finally {
      setDeletingRoomId(null);
    }
  }

  async function handleTemperatureUpdate(temperature: number) {
    try {
      setTemperatureLoading(true);
      const updated = await updateBuildingTemperature({ temperature });
      setBuilding(updated);
      messageApi.success("Building temperature updated successfully");
    } catch (error) {
      messageApi.error(
        error instanceof Error ? error.message : "Failed to update temperature",
      );
    } finally {
      setTemperatureLoading(false);
    }
  }

  return (
    <Layout>
      {contextHolder}

      <Header
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          flexWrap: "wrap",
          gap: "16px",
        }}
      >
        <div>
          <Title level={3} style={{ marginBottom: 0 }}>
            Building Climate Control
          </Title>
          <Paragraph style={{ marginBottom: 0 }}>
            Monitor apartments and common rooms, then adjust the requested
            building temperature.
          </Paragraph>
        </div>
      </Header>
      <Row justify="end" align="middle" gutter={8} style={{ height: "3.5rem" }}>
        <Col>
          <Button
            style={
              {
                //marginBottom: 0,
                /// padding: "5px 2px",
              }
            }
            onClick={() => setAddRoomOpen(true)}
            type="primary"
            size="large"
          >
            Add Room
          </Button>
        </Col>
        <Col>
          <Button onClick={() => void loadBuilding()} size="large">
            Refresh
          </Button>
        </Col>
      </Row>

      <AddRoomModal
        open={addRoomOpen}
        loading={createRoomLoading}
        onCancel={() => setAddRoomOpen(false)}
        onCreateApartment={handleCreateApartment}
        onCreateCommonRoom={handleCreateCommonRoom}
      />

      <EditRoomModal
        open={Boolean(editTarget)}
        loading={editLoading}
        target={editTarget}
        onCancel={() => setEditTarget(null)}
        onUpdateApartment={handleUpdateApartment}
        onUpdateCommonRoom={handleUpdateCommonRoom}
      />

      {errorMessage ? (
        <Alert
          className="section-spacing"
          type="error"
          title="Unable to load building"
          description={errorMessage}
          showIcon
        />
      ) : null}

      {loading ? (
        <div className="loading-shell">
          <Spin size="large" />
        </div>
      ) : null}

      {!loading && building ? (
        <Space orientation="vertical" size={24} style={{ width: "100%" }}>
          <BuildingHeader building={building} />

          <TemperatureControl
            currentTemperature={building.requestedTemperature}
            loading={temperatureLoading}
            onSubmit={handleTemperatureUpdate}
          />

          <Content className="page-content">
            <Space
              align="center"
              style={{
                width: "100%",
                justifyContent: "space-between",
                marginBottom: 16,
              }}
              wrap
            >
              <Title level={3} style={{ margin: 0 }}>
                Apartments
              </Title>
            </Space>

            <Row gutter={[16, 16]}>
              {building.apartmentResponses.map((apartment) => (
                <Col xs={24} md={12} xl={8} key={apartment.room.id}>
                  <RoomCard
                    kind="apartment"
                    apartment={apartment}
                    onEdit={() =>
                      setEditTarget({ kind: "apartment", data: apartment })
                    }
                    onDelete={() =>
                      void handleDeleteApartment(apartment.room.id)
                    }
                    deleteLoading={deletingRoomId === apartment.room.id}
                  />
                </Col>
              ))}
            </Row>
          </Content>

          <Content className="page-content">
            <Space
              align="center"
              style={{
                width: "100%",
                justifyContent: "space-between",
                marginBottom: 16,
              }}
              wrap
            >
              <Title level={3} style={{ margin: 0 }}>
                Common Rooms
              </Title>
            </Space>

            <Row gutter={[16, 16]}>
              {building.rooms.map((commonRoom) => (
                <Col xs={24} md={12} xl={8} key={commonRoom.room.id}>
                  <RoomCard
                    kind="commonRoom"
                    commonRoom={commonRoom}
                    onEdit={() =>
                      setEditTarget({ kind: "commonRoom", data: commonRoom })
                    }
                    onDelete={() =>
                      void handleDeleteCommonRoom(commonRoom.room.id)
                    }
                    deleteLoading={deletingRoomId === commonRoom.room.id}
                  />
                </Col>
              ))}
            </Row>
          </Content>
        </Space>
      ) : null}
    </Layout>
  );
}
