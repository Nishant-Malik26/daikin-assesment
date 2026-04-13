"use client";

import { Form, Input, Modal, Select } from "antd";
import type { CommonRoomType, CreateApartmentRequest, CreateCommonRoomRequest } from "../types/building";
import { COMMON_ROOM_OPTIONS } from "../apiHelpers/api";

type RoomKind = "APARTMENT" | "COMMON_ROOM";

interface AddRoomModalProps {
  open: boolean;
  loading?: boolean;
  onCancel: () => void;
  onCreateApartment: (payload: CreateApartmentRequest) => Promise<void>;
  onCreateCommonRoom: (payload: CreateCommonRoomRequest) => Promise<void>;
}

interface AddRoomFormValues {
  roomKind: RoomKind;
  ownersName?: string;
  unitNumber?: string;
  roomType?: CommonRoomType;
}

export default function AddRoomModal({
  open,
  loading = false,
  onCancel,
  onCreateApartment,
  onCreateCommonRoom,
}: AddRoomModalProps) {
  const [form] = Form.useForm<AddRoomFormValues>();
  const roomKind = Form.useWatch("roomKind", form) ?? "APARTMENT";

  async function handleOk() {
    const values = await form.validateFields();

    if (values.roomKind === "APARTMENT") {
      await onCreateApartment({
        ownersName: values.ownersName!.trim(),
        unitNumber: values.unitNumber!.trim(),
      });
    } else {
      await onCreateCommonRoom({
        roomType: values.roomType!,
      });
    }

    form.resetFields();
  }

  function handleCancel() {
    form.resetFields();
    onCancel();
  }

  return (
    <Modal
      title="Add Room"
      open={open}
      onCancel={handleCancel}
      onOk={() => void handleOk()}
      okText="Create"
      confirmLoading={loading}
      destroyOnHidden
    >
      <Form
        form={form}
        layout="vertical"
        initialValues={{
          roomKind: "APARTMENT",
        }}
      >
        <Form.Item
          label="Room Kind"
          name="roomKind"
          rules={[{ required: true, message: "Please select a room kind" }]}
        >
          <Select
            options={[
              { label: "Apartment", value: "APARTMENT" },
              { label: "Common Room", value: "COMMON_ROOM" },
            ]}
          />
        </Form.Item>

        {roomKind === "APARTMENT" ? (
          <>
            <Form.Item
              label="Unit Number"
              name="unitNumber"
              rules={[
                { required: true, message: "Please enter the unit number" },
                { whitespace: true, message: "Unit number cannot be empty" },
              ]}
            >
              <Input placeholder="e.g. 103" />
            </Form.Item>

            <Form.Item
              label="Owner Name"
              name="ownersName"
              rules={[
                { required: true, message: "Please enter the owner name" },
                { whitespace: true, message: "Owner name cannot be empty" },
              ]}
            >
              <Input placeholder="e.g. Jane Doe" />
            </Form.Item>
          </>
        ) : (
          <Form.Item
            label="Common Room Type"
            name="roomType"
            rules={[{ required: true, message: "Please select a room type" }]}
          >
            <Select
              options={COMMON_ROOM_OPTIONS.map((option) => ({
                label: option,
                value: option,
              }))}
            />
          </Form.Item>
        )}
      </Form>
    </Modal>
  );
}
