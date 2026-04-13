import { Card, Col, Row, Typography } from "antd";
import type { BuildingResponse } from "../types/building";
import styles from "./BuildingHeader.module.css";

const { Title, Text } = Typography;

interface BuildingHeaderProps {
  building: BuildingResponse;
}

export default function BuildingHeader({ building }: BuildingHeaderProps) {
  return (
    <Card className="card-shadow" styles={{ body: { padding: 24 } }}>
      <Title level={3} style={{ marginTop: 0 }}>
        Building Overview
      </Title>

      <Row gutter={[16, 16]}>
        <Col xs={24} sm={12} lg={8}>
          <div className={styles.statBlock}>
            <Text className={styles.statLabel}>Requested Temperature</Text>
            <div className={styles.statValue}>{building.requestedTemperature}°C</div>
          </div>
        </Col>

        <Col xs={24} sm={12} lg={8}>
          <div className={styles.statBlock}>
            <Text className={styles.statLabel}>Apartments</Text>
            <div className={styles.statValue}>{building.apartmentResponses.length}</div>
          </div>
        </Col>

        <Col xs={24} sm={12} lg={8}>
          <div className={styles.statBlock}>
            <Text className={styles.statLabel}>Common Rooms</Text>
            <div className={styles.statValue}>{building.rooms.length}</div>
          </div>
        </Col>
      </Row>
    </Card>
  );
}
