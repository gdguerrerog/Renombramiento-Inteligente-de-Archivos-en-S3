export const EXECUTION_TYPES = ["SUCCESS", "ERROR", "UNMATCHED", "ALREADY_EXECUTED", "DELETE_FAILED", "UNMATCHED_DELETE_FAILED"] as const;
export type ExecutionType = typeof EXECUTION_TYPES[number];

export interface RenameExecution {
  id: string;
  prefixRuleId: number;
  suffixRuleId: number;
  executionTime: string;
  inFileName: string;
  outFileName: string;
  resultType: ExecutionType;
}

export interface FileWithStatus {
  name: string;
  uploadDate: string;
  executions: RenameExecution[]
}
