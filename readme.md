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
