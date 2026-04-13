import { Button, Card, InputNumber, Space, Typography } from "antd";
import { useEffect, useState } from "react";
import styles from "./TemperatureControl.module.css";

const { Title, Paragraph } = Typography;

interface TemperatureControlProps {
  currentTemperature: number;
  loading?: boolean;
  onSubmit: (temperature: number) => void | Promise<void>;
}

export default function TemperatureControl({
  currentTemperature,
  loading = false,
  onSubmit,
}: TemperatureControlProps) {
  const [value, setValue] = useState<number>(currentTemperature);

  useEffect(() => {
    setValue(currentTemperature);
  }, [currentTemperature]);

  return (
    <Card className="card-shadow" styles={{ body: { padding: 24 } }}>
      <Space className={styles.wrapper} align="center" wrap>
        <div>
          <Title level={4} style={{ margin: 0 }}>
            Building Temperature
          </Title>
          <Paragraph style={{ marginBottom: 0, marginTop: 8 }}>
            Set the requested temperature for the whole building.
          </Paragraph>
        </div>

        <Space wrap>
          <InputNumber
            min={10}
            max={40}
            step={0.5}
            value={value}
            onChange={(nextValue) => setValue(nextValue ?? currentTemperature)}
            size="large"
          />
          <Button
            type="primary"
            size="large"
            loading={loading}
            onClick={() => onSubmit(value)}
          >
            Update Temperature
          </Button>
        </Space>
      </Space>
    </Card>
  );
}
