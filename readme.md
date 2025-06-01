## 字軌取號順位
1. 優先使用: 已綁定company && printer && status=1(使用中只會有一個)
2. 第二使用: 已綁定company && printer && status=0(可使用可多個)
3. 第三使用: 已綁定company && status=0(可使用可多個) ，自動綁定 printer 

## roll back exception
* GenerateXmlException
* DuplicateOlderNumberException
* InsufficientBalanceException
* NoTargetAvailableException
* NotEnoughAssignException
* NotfoundOriginalSourceException
* RepeatOperationTargetException
* TargetKeyLockedException

## queue 先以 topic exchange 建立
* 可能會需要1.傳xml 2.成功寄mail 採用分組key模式
* 設計死信轉x-dead-letter-exchange機制，當 queue訊息因為錯誤被拒絕（例如 reject 或 TTL 過期），會轉送到這裡
* 從spring boot bean 自動建立 rabbitmq 對應的queue(但在rabbitmq上exchange要手動先建立)  

### 📦 完整範例說明圖
  [ 原始 queue ]  
  |  
  | 訊息被拒絕 / 過期 / queue 滿  
  v  
  [ x-dead-letter-exchange = dlx-exchange ]  
  |  
  | 使用 routing key "dlx-routing-key"  
  v  
  [ 死信 queue ]

### 📌 什麼情況會產生死信（Dead Letter）？
* 訊息被消費者 拒絕（basic.reject / basic.nack），且 requeue = false 
* 訊息在 queue 中 超過 TTL（存活時間） 
* queue 已滿，訊息被拒收（當有設定 max-length） 
* 消費者連線中斷等異常情況