import type { Metadata } from "next";
import { App, ConfigProvider } from "antd";
import "antd/dist/reset.css";
import "./globals.css";

export const metadata: Metadata = {
  title: "Building Climate Control",
  description: "Apartment building climate control dashboard",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <ConfigProvider
          theme={{
            token: {
              colorPrimary: "#4498CA",
              borderRadius: 14,
              colorBgLayout: "#f4f7fb",
              colorBgContainer: "#ffffff",
              fontSize: 15,
            },
            components: {
              Layout: {
                headerBg: "#5DCAFA",
              },
            },
          }}
        >
          <App>{children}</App>
        </ConfigProvider>
      </body>
    </html>
  );
}
