import { Button, Card, Popconfirm, Space, Tag, Typography } from "antd";
import type { ApartmentResponse, CommonRoomResponse } from "../types/building";
import styles from "./RoomCard.module.css";

const { Title, Paragraph, Text } = Typography;

type RoomCardProps =
  | {
      kind: "apartment";
      apartment: ApartmentResponse;
      onEdit?: () => void;
      onDelete?: () => void;
      deleteLoading?: boolean;
    }
  | {
      kind: "commonRoom";
      commonRoom: CommonRoomResponse;
      onEdit?: () => void;
      onDelete?: () => void;
      deleteLoading?: boolean;
    };

function getModeColor(mode: string) {
  if (mode === "HEATING") return "red";
  if (mode === "COOLING") return "blue";
  return "default";
}

export default function RoomCard(props: RoomCardProps) {
  const room =
    props.kind === "apartment" ? props.apartment.room : props.commonRoom.room;
  const title =
    props.kind === "apartment"
      ? `Apartment ${props.apartment.unitNumber}`
      : props.commonRoom.roomType;

  return (
    <Card className="card-shadow" styles={{ body: { padding: 20 } }}>
      <div className={styles.header}>
        <Title level={5} style={{ margin: 0 }}>
          {title}
        </Title>
        <Tag color={getModeColor(room.acMode)}>{room.acMode}</Tag>
      </div>

      <Space orientation="vertical" size={10} style={{ width: "100%" }}>
        {props.kind === "apartment" ? (
          <Paragraph className={styles.paragraph}>
            <Text strong>Owner:</Text> {props.apartment.ownersName}
          </Paragraph>
        ) : (
          <Paragraph className={styles.paragraph}>
            <Text strong>Type:</Text> {props.commonRoom.roomType}
          </Paragraph>
        )}

        <Paragraph className={styles.paragraph}>
          <Text strong>Temperature:</Text> {room.currentTemperature}°C
        </Paragraph>

        <div className={styles.actions}>
          <Button onClick={props.onEdit}>Edit</Button>

          <Popconfirm
            title="Delete room"
            description="Are you sure you want to delete this room?"
            okText="Delete"
            cancelText="Cancel"
            onConfirm={props.onDelete}
          >
            <Button danger loading={props.deleteLoading}>
              Delete
            </Button>
          </Popconfirm>
        </div>
      </Space>
    </Card>
  );
}
